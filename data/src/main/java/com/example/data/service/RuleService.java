package com.example.data.service;

import com.example.data.dto.RuleDto;
import com.example.data.entity.Rule;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.RuleRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleService {
    RuleRepository ruleRepository;

    public void saveAll(List<RuleDto> list) {
        try {
            ruleRepository.saveAll(mapAll(list));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public Rule findByName(String name) {
        return ruleRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono zasady"));
    }

    public void save(RuleDto ruleDto) {
        try {
            ruleRepository.save(mapToRule(ruleDto));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public void change(RuleDto ruleDto, RuleDto newRuleDto) {
        Rule rule = ruleRepository.findByName(ruleDto.getName())
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono zasady do zmiany"));
        rule.setName(newRuleDto.getName());
        rule.setDescription(newRuleDto.getDescription());
        try {
            ruleRepository.save(rule);
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd zapisu");
        }
    }

    public void delete(String name) {
        Rule rule = ruleRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Brak zasady do usunięcia"));
        try {
            ruleRepository.delete(rule);
        } catch (MongoException e) {
            throw new FailedToDeleteException("Błąd podczas usuwania postaci");
        }
    }

    private Rule mapToRule(RuleDto ruleDto) {
        return new Rule(ruleDto.getName(), ruleDto.getDescription());
    }

    private List<Rule> mapAll(List<RuleDto> list) {
        return list.stream()
                .map(this::mapToRule)
                .toList();
    }
}
