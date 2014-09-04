import java.util.Scanner;

public class Program3
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter two numbers to multiply");
        int m = scan.nextInt();
        int n = scan.nextInt();

        System.out.println("Product is " + mult(m, n, 0, 0, 0));
    }

    public static int mult(int m, int n, int count, int adds, int divs)
    {
        if (n == 1)
        {
            System.out.println("Subprogram ran " + count + " times");
            System.out.println("Made " + adds + " additions");
            System.out.println("Made " + divs + " divisions");
            return m;
        }
        else
        {
            return m + mult(m, n-1, count+1, adds+2, divs+1);
        }
    }
}
