package sda.choir.sdaController;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphController {

    @GetMapping("/graph")
    public String displayGraph(Model model) {
        List<String> labels = Arrays.asList("Label 1", "Label 2", "Label 3");
        List<Integer> dataSeries = Arrays.asList(10, 20, 15);
        
        model.addAttribute("labels", labels);
        model.addAttribute("dataSeries", dataSeries);
        
        return "graph-template"; 
    }
}
