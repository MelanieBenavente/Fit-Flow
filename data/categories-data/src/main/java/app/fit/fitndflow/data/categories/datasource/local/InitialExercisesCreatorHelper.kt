package app.fit.fitndflow.data.categories.datasource.local

import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel

class InitialExercisesCreatorHelper(
    private val categoryDao: CategoryDao,
    private val exerciseDao: ExerciseDao,
) {
    private fun CategoryModel.toEntity() = CategoryEntity(nameEs = name.spanish, nameEn = name.english)
    private fun ExerciseModel.toEntity(categoryId: Int, record: Double = 0.0) = ExerciseEntity(categoryId = categoryId, nameEs = name.spanish, nameEn = name.english, record = record)

    private fun createExercise(es: String, en: String) = ExerciseModel(
        name = StringInLanguagesModel(spanish = es, english = en),
        lastFirstSerie = null,
        record = null
    )

    suspend fun insertInitialCategories() {
        val categoryList = listOf(
            // PECHO
            CategoryModel(
                name = StringInLanguagesModel(spanish = "Pecho", english = "Chest"),
                exerciseList = mutableListOf(
                    createExercise("Press de banca", "Bench Press"),
                    createExercise("Press de banca inclinado", "Incline Bench Press"),
                    createExercise("Press de banca declinado", "Decline Bench Press"),
                    createExercise("Fondos en paralelas", "Dips"),
                    createExercise("Aperturas con mancuernas", "Chest Flyes"),
                    createExercise("Pulldown con polea alta", "Cable Crossover")
                )
            ),
            // ESPALDA
            CategoryModel(
                name = StringInLanguagesModel(spanish = "Espalda", english = "Back"),
                exerciseList = mutableListOf(
                    createExercise("Dominadas", "Pull-ups"),
                    createExercise("Pull-ups (Agarre prono)", "Overhand Pull-ups"),
                    createExercise("Remo con barra T", "T-Bar Row"),
                    createExercise("Pull-down en polea alta", "Lat Pulldown"),
                    createExercise("Peso muerto", "Deadlift"),
                    createExercise("Máquina de polea para dorsales", "Seated Cable Row")
                )
            ),
            // HOMBROS
            CategoryModel(
                name = StringInLanguagesModel(spanish = "Hombros", english = "Shoulders"),
                exerciseList = mutableListOf(
                    createExercise("Press militar", "Military Press"),
                    createExercise("Elevaciones laterales con mancuernas", "Lateral Raises"),
                    createExercise("Elevaciones frontales con barra", "Front Raises"),
                    createExercise("Press Arnold", "Arnold Press"),
                    createExercise("Press de hombros con mancuernas", "Dumbbell Shoulder Press"),
                    createExercise("Face pulls", "Face Pulls")
                )
            ),
            // BÍCEPS
            CategoryModel(
                name = StringInLanguagesModel(spanish = "Bíceps", english = "Biceps"),
                exerciseList = mutableListOf(
                    createExercise("Curl de bíceps con barra", "Barbell Curl"),
                    createExercise("Curl de bíceps con mancuernas", "Dumbbell Curl"),
                    createExercise("Curl martillo", "Hammer Curl"),
                    createExercise("Curl 21s", "21s Curl"),
                    createExercise("Curl de concentración", "Concentration Curl"),
                    createExercise("Curl con polea", "Cable Curl")
                )
            ),
            // TRÍCEPS
            CategoryModel(
                name = StringInLanguagesModel(spanish = "Tríceps", english = "Triceps"),
                exerciseList = mutableListOf(
                    createExercise("Press de tríceps en polea alta", "Triceps Pushdown"),
                    createExercise("Fondos en máquina de tríceps", "Triceps Dip Machine"),
                    createExercise("Extensiones de tríceps con mancuerna", "Triceps Extensions"),
                    createExercise("Tríceps en polea alta con cuerda", "Rope Pushdown"),
                    createExercise("Press de tríceps en máquina Smith", "Smith Machine Close Grip Press"),
                    createExercise("Fondos en máquina de dip", "Dip Machine")
                )
            ),
            // PIERNA
            CategoryModel(
                name = StringInLanguagesModel(spanish = "Pierna", english = "Legs"),
                exerciseList = mutableListOf(
                    createExercise("Sentadillas", "Squats"),
                    createExercise("Prensa de piernas", "Leg Press"),
                    createExercise("Extensiones de cuádriceps", "Leg Extensions"),
                    createExercise("Zancadas", "Lunges"),
                    createExercise("Hack squat", "Hack Squat"),
                    createExercise("Sentadillas búlgaras", "Bulgarian Split Squats"),
                    createExercise("Peso muerto rumano", "Romanian Deadlift"),
                    createExercise("Curl de piernas acostado", "Lying Leg Curl"),
                    createExercise("Curl de piernas sentado", "Seated Leg Curl"),
                    createExercise("Curl de piernas en máquina", "Machine Leg Curl"),
                    createExercise("Prensa de glúteos", "Glute Press")
                )
            )
        )
        for(category in categoryList){
            val id = categoryDao.insertCategory(category.toEntity())
            exerciseDao.insertExercises(category.exerciseList?.map { it.toEntity(id.toInt()) } ?: emptyList())
        }

    }
}