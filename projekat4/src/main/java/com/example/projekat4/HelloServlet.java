package com.example.projekat4;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private List<String> dani=new ArrayList<>();
    private List<Obrok> obroci=new ArrayList<>();
    private Map<String, List<Obrok>> porudzbina=new HashMap<>();


    public void init() {

        dani.add("Ponedeljak");
        dani.add("Utorak");
        dani.add("Sreda");
        dani.add("Cetvrtak");
        dani.add("Petak");
        getServletContext().setAttribute("dani", dani);
        for (String day: dani) {
            try {
                Scanner scanner = new Scanner(new File("D:\\Programiranje\\Veb programiranje\\projekat4\\src\\main\\dani\\" + day + ".txt"));
                while (scanner.hasNextLine()) {
                    String hrana = scanner.nextLine();
                    Obrok obrok = new Obrok(day, hrana);
                    obroci.add(obrok);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }}

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("service method");
        super.service(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        porudzbina= (Map<String, List<Obrok>>) getServletContext().getAttribute("porudzbina");
        if(porudzbina!=null && porudzbina.get(request.getSession().getId())!=null && porudzbina.get(request.getSession().getId()).size()!=0){
            out.println("<h3>" + "Naruceno" + "</h3>");
            List<Obrok>it=porudzbina.get(request.getSession().getId());
            for (Obrok meal: it) {
                out.println("<br>");
                out.println(meal.getDan());
                out.println("<br>");
                out.println("<br>");
                out.println(meal.getHrana());
                out.println("<br>");
                out.println("<br>");

            }
            return;
        }
        getServletContext().setAttribute("obroci", obroci);
        out.println("<html><body><form method=\"POST\" action = \"hello-servlet\">");
        out.println("<h1>" + "Choose your lunch" + "</h1>");
        for (String day: dani) {
            out.println("<h3>" + day + "</h3>");
            out.println("<select name = \""+ day + "\" id=\"" + day + "\">");
            System.out.println("<select name = \" "+ day + "\" id=\"" + day+ "\">" );
            for (Obrok meal: obroci) {
                if (meal.getDan().equals(day)) {
                    out.println("<option value = \"" + meal.getHrana() + "\" selected>" + meal.getHrana() + "</option>");
                }
            }
            out.println("</select><br>");
            out.println("--------------------------------");
        }
        out.println("<br><input type=\"submit\" name\"submit\" value\"Save\"/></form>");
        out.println("</body></html>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Obrok>it=new ArrayList<>();
        for (String day: dani) {
            String obrok = request.getParameter(day);
            System.out.println(obrok);
            Obrok m = vratiObrok(day, obrok);
            it.add(m);
        }
        porudzbina= (Map<String, List<Obrok>>) getServletContext().getAttribute("porudzbina");
        if(porudzbina==null)porudzbina=new HashMap<>();
        porudzbina.put(request.getSession().getId(),it);
        getServletContext().setAttribute("porudzbina",porudzbina);
        response.sendRedirect("/response.html");
    }
    private Obrok vratiObrok(String day, String meal){
        for (Obrok m : obroci) {
            if (m.getDan().equals(day) && m.getHrana().equals(meal)) {
                m.setBrojPorudzbina(m.brojPorudzbina + 1);
                getServletContext().setAttribute("obroci",obroci);
                return m;
            }
        }
        return null;
    }

    public void destroy() {
    }
}