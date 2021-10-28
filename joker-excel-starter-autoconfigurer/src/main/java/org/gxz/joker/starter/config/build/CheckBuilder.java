package org.gxz.joker.starter.config.build;

import lombok.Getter;
import org.gxz.joker.starter.component.UploadAnalysisPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongxuanzhang
 */
@Getter
public class CheckBuilder extends JoinAbleBuilder {

    List<UploadAnalysisPostProcessor> processors;


    public CheckBuilder(JokerBuilder jokerBuilder) {
        super(jokerBuilder);
    }

    public CheckBuilder uploadCheck(UploadAnalysisPostProcessor processor) {
        if (processors == null) {
            processors = new ArrayList<>();
        }
        processors.add(processor);
        return this;
    }

}
