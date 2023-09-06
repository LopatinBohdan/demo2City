package Controllers;

import com.example.demo2city.Models.City;
import com.example.demo2city.Repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private CityRepository repository;
    @GetMapping("/")
    public  String home(Model model){
        Iterable<City> cities=repository.findAll();
        model.addAttribute("cities", cities);
        return "home";
    }
    @GetMapping("/add")
    public String addCityForm(Model model){
        return "/addCity";
    }
    @PostMapping("/add")
    public String addCity(@RequestParam String title, @RequestParam int population, @RequestParam String history,
                             @RequestParam int longitude, @RequestParam int latitude, Model model){
        City city=new City(title, population,history,longitude,latitude);
        repository.save(city);
        return "redirect:/";
    }

    @GetMapping("/{id}/info")
    public String infoCity(@PathVariable(value = "id") Long id, Model model){
        if(!repository.existsById(id)){
            return "redirect:/";
        }
        else{
            City city=repository.findById(id).get();
            model.addAttribute("city", city);
            return "infoCity";
        }
    }

    @GetMapping("/{id}/delete")
    public String cityDelete(@PathVariable(value = "id") Long id, Model model){
        City city=repository.findById(id).get();
        repository.delete(city);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String cityEditForm(@PathVariable(value = "id")Long id, Model model){
        if(!repository.existsById(id)){
            return "redirect:/";
        }
        else{
            City city=repository.findById(id).get();
            model.addAttribute("city", city);
            return "editCity";
        }
    }
    @PostMapping("/{id}/edit")
    public String cityEdit(@PathVariable(value = "id")Long id,@RequestParam String title, @RequestParam int population,
                           @RequestParam String history , @RequestParam int longitude, @RequestParam int latitude,
                           Model model){

        City city=repository.findById(id).get();
        city.setTitle(title);
        city.setPopulation(population);
        city.setHistory(history);
        city.setLongitude(longitude);
        city.setLatitude(latitude);
        repository.save(city);
        return "redirect:/";
    }
}
