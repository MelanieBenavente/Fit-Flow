package app.fit.fitndflow.ui.features.training

import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete
import app.fit.fitndflow.ui.features.common.CommonFragment

class AddSerieTrainingFragment: CommonFragment(), TrainingCallback, DialogCallbackDelete {
    override fun onClickAcceptDelete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun clickListenerInterfaceAdapter(input: SerieModel?) {
        TODO("Not yet implemented")
    }
}