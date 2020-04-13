import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/homePage", loadOnStartup = 1)
public class HomePage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
            HttpSession session = request.getSession();
            List<Product> productList = getProducts(request, session);
            cartSum(session, productList);
            printForm(response);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Incorrect value");
            printForm(response);
        }


        //request.getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // HttpSession session = request.getSession();
        // request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        try {
            printForm(response);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Incorrect value");
            printForm(response);
        }
    }

    private void cartSum(HttpSession session, List<Product> productList) {
        BigDecimal cartSum = new BigDecimal(0);

        for (Product product1 : productList
        ) {
            cartSum = cartSum.add(product1.getPrice());
        }
        session.setAttribute("cartSum", cartSum);
    }

    private List<Product> getProducts(HttpServletRequest request, HttpSession session) {
        String productName = request.getParameter("productName");
        BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(request.getParameter("quantity")));
        double priceFromUser = Double.parseDouble(request.getParameter("price"));

        BigDecimal price = new BigDecimal(String.valueOf(priceFromUser));
        price = price.setScale(2, RoundingMode.CEILING);


        Product product = new Product(productName, quantity, price);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", productList);
        } else {
            productList = (List<Product>) session.getAttribute("cart");
            productList.add(product);
            session.setAttribute("cart", productList);
        }
        return productList;
    }

    private void printForm(HttpServletResponse response) throws IOException {
        String form =
                "<html>\n" +
                        "<head>\n" +
                        "    <title>Title</title>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <form action=\"homePage\" method=\"post\">\n" +
                        "        <label>Insert product:\n" +
                        "        <input type=\"text\" id=\"productName\" name=\"productName\">\n" +
                        "        </label><br>\n" +
                        "        <label>Insert quantity:\n" +
                        "            <input type=\"number\" id=\"quantity\" name=\"quantity\">\n" +
                        "        </label><br>\n" +
                        "        <label>Insert price:\n" +
                        "            <input type=\"text\" id=\"price\" name=\"price\">\n" +
                        "        </label><br>\n" +
                        "        <button>Add to shopping cart</button>\n" +
                        "<button><a href=\"/cart\">Cart</a></button><br>" +
                        "    </form>\n" +
                        "</body>\n" +
                        "</html>";

        response.getWriter().println(form);
    }
}


