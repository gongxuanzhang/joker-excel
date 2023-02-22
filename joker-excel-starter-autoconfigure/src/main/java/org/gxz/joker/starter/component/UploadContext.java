package org.gxz.joker.starter.component;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class UploadContext {
    /**
     * 上传的文件
     **/
    private MultipartFile multipartFile;


}
