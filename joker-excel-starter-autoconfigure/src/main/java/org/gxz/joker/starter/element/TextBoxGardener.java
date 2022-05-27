package org.gxz.joker.starter.element;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTextBox;
import org.gxz.joker.starter.annotation.TextBox;
import org.gxz.joker.starter.element.gardener.Gardener;
import org.gxz.joker.starter.service.ColumnRule;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;

import java.awt.*;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class TextBoxGardener implements Gardener {

    private final TextBox textBox;

    public TextBoxGardener(TextBox textBox) {
        this.textBox = textBox;
    }

    @Override
    public void clip(Sheet sheet, List<ColumnRule> ruleList) {
        XSSFDrawing draw = ((XSSFSheet) sheet).createDrawingPatriarch();
        int index = ruleList.size();
        XSSFClientAnchor createAnchor = draw.createAnchor(0, 0, 0, 0, index + 1, 3, index + 1+ textBox.width(), 3 + textBox.height());

        //创建文本框
        XSSFTextBox tb1 = draw.createTextbox(createAnchor);

        //设置边框颜色，黑色
        tb1.setLineStyleColor(0, 0, 0);

        //设置填充色，白色
        Color col = Color.white;
        tb1.setFillColor(col.getRed(), col.getGreen(), col.getBlue());

        //富文本字符串
        XSSFRichTextString address = new XSSFRichTextString(textBox.value());
        tb1.setText(address);
        //文字字符属性
        CTTextCharacterProperties rpr = tb1.getCTShape().getTxBody().getPArray(0).getRArray(0).getRPr();
        //设置字体
        rpr.addNewLatin().setTypeface("Trebuchet MS");
        //设置字体大小1000
        rpr.setSz(1000);

    }
}
