package com.example.projekat4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "spisak", value = "/spisak")
public class Spisak extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String lozinka = request.getParameter("lozinka");
        Scanner scanner = new Scanner(new File("D:\\Programiranje\\Veb programiranje\\projekat4\\src\\main\\dani\\password.txt"));
        boolean flag=lozinka.equals(scanner.nextLine());
        if(flag) {
            out.println("<html><body><form method=\"POST\" action = \"spisak?lozinka=domaci\">");
            out.println("<h1>" + "Meals" + "</h1>");
            out.println("<html><body>");

            List<Obrok> obroci = (List<Obrok>) getServletContext().getAttribute("obroci");
            List<String> dani = (List<String>) getServletContext().getAttribute("dani");

            for(String day: dani) {
                out.println("<h1>" + day + "</h1>");
                out.println("<h1></h1>");
                out.println("<table style=\"width:100 % \">");
                out.println("<tr> <th>#</th> <th>Meal</th> <th>Quantity</th> </tr>");
                int j = 0;
                for (Obrok meal : obroci) {
                    if (meal.getDan().equals(day)) {
                        j++;
                        out.println("<tr> <th>" + j + "</th> <th>" + meal.getHrana() + "</th> <th>" + meal.getBrojPorudzbina() + "</th> </tr>");
                    }
                }
                out.println("<style>\n" +
                        "table, th, td {\n" +
                        "  border: 1px solid black;\n" +
                        "}\n" +
                        "</style></table>\n");
                out.println("<h1></h1>");
            }

            out.println("--------------------------<br>");
            out.println("<br><input type=\"submit\" name\"submit\" value\"Clear\"/></form>");
            out.println("</body></html>");
        }
        else {
            out.println("<h3>" +"Pogresna lozinka" + "</h3>");
        }
        scanner.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Obrok> obroci = (List<Obrok>) getServletContext().getAttribute("obroci");
        Map<String, List<Obrok>> map = (Map<String, List<Obrok>>) getServletContext().getAttribute("porudzbina");
        for (Map.Entry<String, List<Obrok>> m: map.entrySet()){
            m.setValue(null);
        }
        for (Obrok o: obroci) {
            o.setBrojPorudzbina(0);
        }
        getServletContext().setAttribute("porudzbina", map);
        getServletContext().setAttribute("obroci", obroci);
    }


}
