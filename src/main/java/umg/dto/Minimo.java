package umg.dto;

public class Minimo {
    private Integer i;
    private Integer j;
    private Integer valor;

    public Minimo(Integer i, Integer j, Integer value) {
        this.i = i;
        this.j = j;
        this.valor = value;
    }

    public Minimo() {
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Minimo{" +
                "i=" + i +
                ", j=" + j +
                ", valor=" + valor +
                '}';
    }
}
