package com.example.LukeriaFrontendApplication.controllers;

import com.example.LukeriaFrontendApplication.config.*;
import com.example.LukeriaFrontendApplication.dtos.CartonDTO;
import com.example.LukeriaFrontendApplication.dtos.ClientDTO;
import com.example.LukeriaFrontendApplication.dtos.MaterialOrderDTO;
import com.example.LukeriaFrontendApplication.dtos.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/material-order")
public class MaterialOrderController {
    private final MaterialOrderClient materialOrderClient;
    private final CartonClient cartonClient;
    private final PackageClient packageClient;
    private final PlateClient plateClient;
    private static final String ORDERTXT = "order";
    private static final String REDIRECTTXT = "redirect:/material-order/show";

    @GetMapping("/create")
    String createMaterialOrder(Model model) {
        MaterialOrderDTO materialOrderDTO = new MaterialOrderDTO();
        model.addAttribute("cartons", cartonClient.getAllCartons());
        model.addAttribute("packages", packageClient.getAllPackages());
        model.addAttribute("plates", plateClient.getAllPlates());
        model.addAttribute(ORDERTXT, materialOrderDTO);
        return "MaterialOrder/create";
    }
    @GetMapping("/show")
    public String index(Model model) {
        List<MaterialOrderDTO> materialOrderDTOS = materialOrderClient.getAllMaterialOrders();;
        model.addAttribute("orders", materialOrderDTOS);
        return "MaterialOrder/show";
    }

    @PostMapping("/submit")
    public ModelAndView submitMaterialOrder(@ModelAttribute("order") MaterialOrderDTO materialOrderDTO) {
        materialOrderClient.createMaterialOrder(materialOrderDTO);
        return new ModelAndView("redirect:/index");
    }
    @PostMapping("/delete/{id}")
    ModelAndView deleteMaterialOrderById(@PathVariable("id") Long id, Model model) {
        materialOrderClient.deleteMaterialOrderById(id);
        return new ModelAndView(REDIRECTTXT);
    }
    @GetMapping("/edit/{id}")
    String editMaterialOrder(@PathVariable(name = "id") Long id, Model model) {
        MaterialOrderDTO existingOrder = materialOrderClient.getMaterialOrderById(id);
        model.addAttribute("cartons", cartonClient.getAllCartons());
        model.addAttribute("packages", packageClient.getAllPackages());
        model.addAttribute("plates", plateClient.getAllPlates());
        model.addAttribute(ORDERTXT, existingOrder);
        return "OrderProduct/edit";
    }
}
