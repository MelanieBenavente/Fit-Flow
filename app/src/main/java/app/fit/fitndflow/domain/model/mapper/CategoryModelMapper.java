package app.fit.fitndflow.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ExcerciseDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExcerciseModel;


public class CategoryModelMapper {

    public static List<CategoryModel> toModel(List<CategoryDto> categoryDtoList) {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtoList) {
            CategoryModel categoryModel = new CategoryModel(categoryDto.id, categoryDto.name.spanish, new ArrayList<>());
            for (ExcerciseDto excerciseDto : categoryDto.excerciseDtoList) {
                ExcerciseModel exerciseModel = new ExcerciseModel(excerciseDto.id, excerciseDto.exerciseName.spanish);
                categoryModel.getExcerciseList().add(exerciseModel);
            }
            categoryModelList.add(categoryModel);
        }
        return categoryModelList;
    }
}