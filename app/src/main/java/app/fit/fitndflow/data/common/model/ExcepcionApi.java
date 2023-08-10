package app.fit.fitndflow.data.common.model;

public class ExcepcionApi extends Exception {


    private int codigo;


    public ExcepcionApi(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
