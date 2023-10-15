package app.fit.fitndflow.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ExcerciseDto;
import app.fit.fitndflow.domain.model.CategoryModelKT;
import app.fit.fitndflow.domain.model.ExcerciseModel;


public class CategoryModelKTMapper {

    public static List<CategoryModelKT> toModel(List<CategoryDto> categoryDtoList) {
        List<CategoryModelKT> categoryModelKTList = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtoList) {
            CategoryModelKT CategoryModelKT = new CategoryModelKT(categoryDto.id, categoryDto.name.spanish, new ArrayList<>());
            for (ExcerciseDto excerciseDto : categoryDto.excerciseDtoList) {
                ExcerciseModel exerciseModel = new ExcerciseModel(excerciseDto.id, excerciseDto.exerciseName.spanish);
                CategoryModelKT.getExcerciseList().add(exerciseModel);
            }
            categoryModelKTList.add(CategoryModelKT);
        }
        return categoryModelKTList;
    }
}