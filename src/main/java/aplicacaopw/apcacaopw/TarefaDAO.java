package aplicacaopw.apcacaopw;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TarefaDAO {
    private Conexao c;
    private String IT = "INSERT INTO toDo (texto,prioridade,data) VALUES(?,?,?)";
    private String BTI = "SELECT * FROM toDo WHERE id = ?";
    private String BT = "SELECT * FROM toDo";
    private String AT = "UPDATE toDO SET texto = ?, prioridade=? ,data=? WHERE id = ? ";
    private String DT = "DELETE * FROM toDo WHERE id =?";

    String dbUri = System.getenv("DATABASE_HOST");
    String dbPort = System.getenv("DATABASE_PORTA");
    String dbName = System.getenv("DATABASE_NAME");

    public TarefaDAO(){
        c = new Conexao("postgres", "123", "jdbc:postgresql://localhost:5432/Tarefa");
    }

    public void InserirTarefa(Tarefa t) {
        try{
            c.conectar();
            PreparedStatement instrucao = c.getminhaconexao().prepareStatement(IT);
            instrucao.setString(1, t.getTexto());
            instrucao.setInt(2, t.getPrioridade());
            instrucao.setFloat(3, t.getData().getTime());
            instrucao.executeUpdate();
            c.desconectar();
        }catch(Exception e){
            System.out.println("Erro");
        }
       
    }

    public Tarefa BuscarTarefapeloID(int id){
        Tarefa t = null;
        try{
            c.conectar();
            PreparedStatement instrucao = c.getminhaconexao().prepareStatement(BTI);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if(rs.next()){
                t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTexto(rs.getString("texto"));
                t.setPrioridade(rs.getInt("prioridade"));
                t.setData(new Date( (long) rs.getFloat("data")));
            }
            c.desconectar();
        }catch(Exception e){
            System.out.println("erro aqui");
        }
        return t;
    }

    public ArrayList<Tarefa> BuscarTarefas(){
        Tarefa t;
        ArrayList<Tarefa> tLista = new ArrayList<Tarefa>();
        try{
            c.conectar();
            Statement instrucao = c.getminhaconexao().createStatement();
            ResultSet rs =instrucao.executeQuery(BT);
            while(rs.next()){
                t = new Tarefa(rs.getInt("id"), rs.getString("texto"),rs.getInt("prioridade"),new Date( (long) rs.getFloat("data")));
                tLista.add(t);
            }
            c.desconectar();
        }catch(Exception e){
            System.out.println("Erro na Busca de Tarefas");
        }
        return tLista;
        
    }
    public void alterarTarefa(Tarefa t) {
        try{
            c.conectar();
            PreparedStatement instrucao = c.getminhaconexao().prepareStatement(AT);
            instrucao.setString(1, t.getTexto());
            instrucao.setInt(2, t.getPrioridade());
            instrucao.setFloat(3, t.getData().getTime());
            instrucao.setInt(4, t.getId());
            instrucao.executeUpdate();
            c.desconectar();
        }catch(Exception e){
            System.out.println("Erro na Alteracao");
        }
       
    }

    public void deletarTarefa(int id) {
        try{
            c.conectar();
            PreparedStatement instrucao = c.getminhaconexao().prepareStatement(DT);
            instrucao.setInt(1, id);
            instrucao.executeUpdate();
            c.desconectar();
        }catch(Exception e){
            System.out.println("Erro na Exclusao");
        }
       
    }

}
