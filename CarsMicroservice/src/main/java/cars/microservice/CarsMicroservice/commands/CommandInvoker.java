package cars.microservice.CarsMicroservice.commands;

import org.springframework.stereotype.Component;

@Component
public class CommandInvoker {
    public void execute(AdCommand command) throws Exception {
        command.execute();
    }

}