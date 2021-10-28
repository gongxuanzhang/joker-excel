package org.gxz.joker.starter.config.build;

import lombok.Getter;
import org.gxz.joker.starter.component.UploadCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongxuanzhang
 */
@Getter
public class CheckBuilder extends JoinAbleBuilder {

    List<UploadCheck> processors;


    public CheckBuilder(JokerBuilder jokerBuilder) {
        super(jokerBuilder);
    }

    public CheckBuilder uploadCheck(UploadCheck processor) {
        if (processors == null) {
            processors = new ArrayList<>();
        }
        processors.add(processor);
        return this;
    }


}
