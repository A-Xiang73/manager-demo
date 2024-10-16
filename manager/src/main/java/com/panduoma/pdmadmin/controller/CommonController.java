package com.panduoma.pdmadmin.controller;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.panduoma.pdmadmin.exception.BusinessException;
import com.panduoma.pdmadmin.response.R;
import com.panduoma.pdmadmin.response.ResponseCode;
import com.panduoma.pdmadmin.util.Captcha;
import com.panduoma.pdmadmin.util.CaptchaCache;
import com.panduoma.pdmadmin.util.UploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
@Tag(name = "系统基础功能")
@RestController
public class CommonController {
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private CaptchaCache captchaCache;

    @Value("${file.server.dir}")
    private String fileDir = "";
    @Value("${file.server.path}")
    private String path = "";

    /**
     * 获取图片验证码
     *
     * @return
     */
    @Operation(summary = "获取图片验证码")
    @RequestMapping("/common/getCaptcha")
    @CrossOrigin
    public R<Captcha> getCaptcha() {
        String captchaText = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(captchaText);
        String base64Code;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", os);
            base64Code = Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.CREATE_CAPTCHA_ERROR);

        }
        /*
         * 返回验证码对象
         * 图片 base64格式
         */
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Captcha captchaVO = new Captcha();
        captchaVO.setCaptchaId(uuid);
        captchaVO.setCaptchaImage("data:image/png;base64," + base64Code);
        captchaCache.storeCaptcha(uuid, captchaText);
        return R.data(captchaVO);
    }

    /*
     * 上传文件
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Operation(summary = "上传文件")
    @RequestMapping("/common/uploadFile")
    @CrossOrigin
    public R<UploadFile> upload(HttpServletRequest req, HttpServletResponse res, @RequestParam("file") MultipartFile file) throws ServletException, IOException {
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf("."));
        String fname = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
        String basePath = fileDir;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String furl = path + File.separator + fname;
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + furl;
        File dest = new File(basePath, fname);
        try {
            file.transferTo(dest);
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(furl);
            uploadFile.setUrl(url);
            return R.data(uploadFile);
        } catch (IOException e) {
            return R.fail();
        }
    }

    @Operation(summary = "下载文件")
    @GetMapping("/common/downFile")
    @CrossOrigin
    public void downFile(HttpServletRequest req, HttpServletResponse res) {
        String filePath = req.getParameter("filePath");
        filePath = filePath.substring(filePath.indexOf(path) + 1);
        String basePath = fileDir;
        String fileName = basePath + filePath;
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + filePath);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
