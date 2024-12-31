import example.processor.EventHolderProcessor;

import javax.annotation.processing.Processor;

module example.processor {
    requires example.ann;

    requires jdk.compiler;

    provides Processor with EventHolderProcessor;
}