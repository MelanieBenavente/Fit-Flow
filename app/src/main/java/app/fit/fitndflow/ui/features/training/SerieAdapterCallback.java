package app.fit.fitndflow.ui.features.training;

import app.fit.fitndflow.domain.model.ExerciseModel;

public interface SerieAdapterCallback {

    void showSeries(ExerciseModel exercise);

    //todo crear metodos para mostrar dialogos: el de creacion de un nuevo ejercicio y el de eliminar ejercicio (igual que en categoryadaptercallback)
}
