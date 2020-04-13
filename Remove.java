import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@WebServlet("/remove")
public class Remove extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            remove(request);
            JOptionPane.showMessageDialog(null, "Product removed");
            request.getServletContext().getRequestDispatcher("/homePage").forward(request, response);
        } catch (ConcurrentModificationException e) {
            remove(request);
            JOptionPane.showMessageDialog(null, "Product removed");
            request.getServletContext().getRequestDispatcher("/homePage").forward(request, response);
        }
    }

    private void remove(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<Product> productList = (List<Product>) session.getAttribute("cart");
        String product = request.getParameter("product");
        List<Product> productList1 = new ArrayList<>();
        BigDecimal cartSum = (BigDecimal) session.getAttribute("cartSum");

        for (Product p : productList) {
            if (p.getProductName().equals(product)) {
                productList.remove(p);
                cartSum = cartSum.subtract(p.getPrice());
            }
            productList1.add(p);
        }
        session.setAttribute("cart", productList1);
        session.setAttribute("cartSum", cartSum);
    }
}
