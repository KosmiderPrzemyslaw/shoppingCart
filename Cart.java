import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/cart")
public class Cart extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            printCart(request, response);
        } catch (NullPointerException e) {
            response.getWriter().println("Your cart is empty!");
            String backToShopping = "<html><br><a href=\"/homePage\"><button>Back to shopping</button></a></html>";
            response.getWriter().println(backToShopping);
        }

    }

    private void printCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<Product> productList = (List<Product>) session.getAttribute("cart");
        BigDecimal cartSum = (BigDecimal) session.getAttribute("cartSum");
        session.setAttribute("cartSum", cartSum);


        response.getWriter().println("<html><h1>Shopping cart<br></h1></html>");

        for (Product product : productList
        ) {

            response.getWriter().println("Product: " + product.getProductName());
            response.getWriter().println("Quantity: " + product.getQuantity());
            response.getWriter().println("Price: " + product.getPrice() + "$");
            response.getWriter().println("<html><a href=\"/remove?product=" + product.getProductName() +"\"><button>Remove</button><br></a></html>");

        }





        response.getWriter().println(String.format("\nTotal: %s %s", cartSum, "$"));
        String backToShopping = "<html><br><a href=\"/homePage\"><button>Back to shopping</button></a></html>";
        response.getWriter().println(backToShopping);
    }
}
