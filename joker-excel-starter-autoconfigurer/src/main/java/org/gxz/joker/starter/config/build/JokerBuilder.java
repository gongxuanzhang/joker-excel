package org.gxz.joker.starter.config.build;


import lombok.Getter;

/**
 * Joker配置建造者，
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Getter
public class JokerBuilder {
    JokerUploadBuilder jokerUploadBuilder;

    JokerExportBuilder jokerExportBuilder;

    HeadBuilder headBuilder;


    public JokerUploadBuilder upload() {
        if (this.jokerUploadBuilder == null) {
            this.jokerUploadBuilder = new JokerUploadBuilder(this);
        }
        return this.jokerUploadBuilder;
    }

    public JokerExportBuilder export() {
        if (this.jokerExportBuilder == null) {
            this.jokerExportBuilder = new JokerExportBuilder(this);
        }
        return this.jokerExportBuilder;
    }

    public HeadBuilder head() {
        if (this.headBuilder == null) {
            this.headBuilder = new HeadBuilder(this);
        }
        return this.headBuilder;
    }


}
