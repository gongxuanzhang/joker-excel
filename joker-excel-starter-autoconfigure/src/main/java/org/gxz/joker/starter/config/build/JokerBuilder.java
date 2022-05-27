package org.gxz.joker.starter.config.build;


/**
 * Joker配置建造者，
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerBuilder {
    JokerUploadBuilder jokerUploadBuilder;

    JokerExportBuilder jokerExportBuilder;

    HeadBuilder headBuilder;

    BodyBuilder bodyBuilder;

    CheckBuilder checkBuilder;

    ExpressionBuilder expressionBuilder;


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

    public CheckBuilder check() {
        if (this.checkBuilder == null) {
            this.checkBuilder = new CheckBuilder(this);
        }
        return this.checkBuilder;
    }

    public ExpressionBuilder expression() {
        if (this.expressionBuilder == null) {
            this.expressionBuilder = new ExpressionBuilder(this);
        }
        return expressionBuilder;
    }

    public BodyBuilder body(){
        if(this.bodyBuilder == null){
            this.bodyBuilder = new BodyBuilder(this);
        }
        return this.bodyBuilder;
    }


}
