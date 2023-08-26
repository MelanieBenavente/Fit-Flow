package app.fit.fitndflow.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ExerciseDto;
import app.fit.fitndflow.domain.model.ItemModel;

public class CategoryModelMapper {

    public static List<ItemModel> toModel(List<CategoryDto> categoryDtoList){
        List<ItemModel> categoryModelList = new ArrayList<>();
        for(CategoryDto categoryDto : categoryDtoList){
            ItemModel categoryModel = new ItemModel(categoryDto.name.spanish, new ArrayList<>());
            for(ExerciseDto exerciseDto : categoryDto.exerciseDtoList){
                ItemModel exerciseModel = new ItemModel(exerciseDto.exerciseName.spanish);
                categoryModel.getExerciseList().add(exerciseModel);
            }
            categoryModelList.add(categoryModel);
        }
        return categoryModelList;
    }
}
