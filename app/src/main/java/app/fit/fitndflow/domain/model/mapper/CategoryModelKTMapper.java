package app.fit.fitndflow.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ExerciseDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;


public class CategoryModelKTMapper {

    public static List<CategoryModel> toModel(List<CategoryDto> categoryDtoList) {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtoList) {
            CategoryModel CategoryModel = new CategoryModel(categoryDto.getId(), categoryDto.getName().getSpanish(), new ArrayList<>());
            for (ExerciseDto exerciseDto : categoryDto.getExerciseDtoList()) {
                ExerciseModel exerciseModel = new ExerciseModel(exerciseDto.getId(), exerciseDto.getExerciseName().getSpanish());
                CategoryModel.getExerciseList().add(exerciseModel);
            }
            categoryModelList.add(CategoryModel);
        }
        return categoryModelList;
    }
}