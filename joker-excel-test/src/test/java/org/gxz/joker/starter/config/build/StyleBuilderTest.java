package org.gxz.joker.starter.config.build;

import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.style.StylerUtils;

import java.util.List;
import java.util.function.Consumer;


/**
 *
 * 测试Builder的链式调用
 **/
class StyleBuilderTest {


    private Consumer<CellStyle> styleConsumer = (s)-> s.setLocked(true);

    private JokerBuilder jokerBuilder = new JokerBuilder();


    @Test
    @DisplayName("通过Class直接设置样式")
    public void classStyle(){
        jokerBuilder.head()
                .whenClass(StyleBuilderTest.class).style(styleConsumer)
        .whenClass(String.class).style(styleConsumer);

        List<ClassStyleBuilder> classStyleBuilderList = jokerBuilder.headBuilder.getClassStyleBuilderList();
        Assertions.assertEquals(2,classStyleBuilderList.size());
        Assertions.assertEquals(classStyleBuilderList.get(0).getBeanType(),StyleBuilderTest.class);
        Assertions.assertEquals(classStyleBuilderList.get(1).getBeanType(),String.class);
        Assertions.assertEquals(classStyleBuilderList.get(0).styleConsumer,styleConsumer);
        Assertions.assertEquals(classStyleBuilderList.get(1).styleConsumer,styleConsumer);

    }

    @Test
    @DisplayName("通过Class设置值和字段")
    public void multiClass(){

    }

}
