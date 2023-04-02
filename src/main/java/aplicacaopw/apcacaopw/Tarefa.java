package aplicacaopw.apcacaopw;

import java.util.Date;

public class Tarefa {
    private int id;
    private String texto;
    private int prioridade;
    private Date data;



    public Tarefa(int id, String texto, int prioridade, Date data) {
        this.id = id;
        this.texto = texto;
        this.prioridade = prioridade;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tarefa(){
        data = new Date();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }    
}
