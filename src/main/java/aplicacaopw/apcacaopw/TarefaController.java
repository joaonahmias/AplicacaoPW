package aplicacaopw.apcacaopw;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TarefaController {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
    public void cadastraTarefa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int i;
        Tarefa tarefa = new Tarefa();
        ArrayList<Tarefa> tLista = new ArrayList <Tarefa>();
        TarefaDAO tDAO = new TarefaDAO();
        tarefa.setTexto(request.getParameter("texto"));
        tarefa.setPrioridade(Integer.parseInt(request.getParameter("prioridade")));
        
        tDAO.InserirTarefa(tarefa);
        
        response.setContentType("text/HTML");
        var writer = response.getWriter();
        writer.println("<html>"+"<body>"+"<h1>Tarefa Cadastrada<h1>"+
        "<p>Texto: "+ tarefa.getTexto()+"</p>"+
        "<p>Prioridade: " + tarefa.getPrioridade() +"</p>"+
        "<p> Data: "+tarefa.getData() + "</p>");
        
        tLista = tDAO.BuscarTarefas();

        for(i=0;i<tLista.size();i++){
           writer.println("<hr /><p>Tarefa "+(i+1)+"</p>");
           writer.println("<p>Texto: "+ tLista.get(i).getTexto()+"</p>"+
            "<p>Prioridade: "+ tLista.get(i).getPrioridade() +"</p>"+
            "<p>Data: "+tLista.get(i).getData() + "</p>");
            writer.println("<a href=\"/mostrarPaginaAlterarTarefa?id=" + tLista.get(i).getId() + "\">Atualizar</a>");
        }

        writer.println("</body>"+"</html>");

        writer.close();
    }

    @GetMapping("/mostrarPaginaAlterarTarefa")
public String mostrarPaginaAlterarTarefa(@RequestParam int id, Model model) {
    TarefaDAO tDAO = new TarefaDAO();
    // buscar a tarefa pelo id
    Tarefa tarefa = tDAO.BuscarTarefapeloID(id);
    model.addAttribute("id", tarefa.getId());

    return "Alterar.html";
}

    @RequestMapping(method = RequestMethod.POST, value =  "/buscarpeloid")
    public void BuscarTarefapeloID(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Tarefa tarefa = new Tarefa();
        TarefaDAO tDAO = new TarefaDAO();
        tarefa.setId(Integer.parseInt(request.getParameter("id")));
        tarefa = tDAO.BuscarTarefapeloID(tarefa.getId());
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<html><body><h1>Buscar pelo Id</h1>");
        if(tarefa!=null){
           writer.println( "<p>Id: "+ tarefa.getId()+ "</p>"+
            "<p>Tarefa: "+ tarefa.getTexto()+ "</p>"+
            "<p>Prioridade: "+ tarefa.getPrioridade()+ "</p>"+
            "<p>Data:  "+ tarefa.getData()+ "</p>");
        }
        else{
            writer.println("<p>Tarefa não cadastrada</p>");  
        }
        writer.println("</body></html>");
        
        
    }

    
    @RequestMapping(method = RequestMethod.POST, value = "/alterar")
    public void AlterarTarefa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Tarefa tarefa = new Tarefa();
        TarefaDAO tDAO = new TarefaDAO();
        tarefa.setId(Integer.parseInt(request.getParameter("id")));
        tarefa.setTexto(request.getParameter("texto"));
        tarefa.setPrioridade(Integer.parseInt(request.getParameter("prioridade")));
        try {
            tarefa.setData (dateFormat.parse(request.getParameter("data")));
        } catch (ParseException e) {
           System.out.println("Formato de data invalido");
        }
        tDAO.alterarTarefa(tarefa);
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<p>Tarefa Alterada</p>");
        writer.close();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deletar")
    public void DeletarTarefa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        TarefaDAO tDAO = new TarefaDAO();
        tDAO.deletarTarefa(Integer.parseInt(request.getParameter("id")));
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<p>Tarefa Excluida</p>");
        writer.close();
    }
    
}
