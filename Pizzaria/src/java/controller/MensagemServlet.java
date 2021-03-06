package controller;

import model.IMensagemManager;
import model.ITopicoManager;
import model.Mensagem;
import model.MensagemManager;
import model.TopicoManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static jdk.nashorn.internal.runtime.Debug.id;


public class MensagemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id;
        String idParam;
        idParam = request.getParameter("id");
        id = Integer.parseInt(idParam);
        ArrayList<Mensagem> mensagens;
        IMensagemManager manager;
        manager = new MensagemManager();
        mensagens = manager.getMensagem(id);
        request.setAttribute("mensagens",mensagens); 
        ITopicoManager Tmanager;
        Tmanager = new TopicoManager();
        Tmanager.incrementaAcesso(id);
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/WEB-INF/jsp/mensagem.jsp");
        rd.forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Mensagem mensagem = new Mensagem();
        mensagem.setText(request.getParameter("text")); // parametro que esta no campo name
        String idaux;
        idaux = request.getParameter("id");
        int id_topico =Integer.parseInt(idaux);
        mensagem.setTopico_id(id_topico);
        mensagem.setUsuario(request.getParameter("usuario"));
        Date data = new Date();
        mensagem.setData(data);
        MensagemManager manager;
        manager = new MensagemManager();
        manager.criarMensagem(mensagem);
        manager.incrementaMensagem(id_topico);
        ArrayList<Mensagem> mensagens;
        manager = new MensagemManager();
        mensagens = manager.getMensagem(id_topico);
        request.setAttribute("mensagens",mensagens); 
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/WEB-INF/jsp/mensagem.jsp");
        rd.forward(request, response);
    }
}
