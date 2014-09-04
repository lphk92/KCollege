import java.util.Scanner;

public class Program4
{
    public static void main (String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a number");
        int n = scan.nextInt();

        System.out.println("Floor Log is " + floorLg(n, 0, 0, 0));
    }

    public static int floorLg(int n, int count, int adds, int divs)
    {
        if (n == 1)
        {
            System.out.println("Subprogram ran " + count + " times");
            System.out.println("Made " + adds + " additions");
            System.out.println("Made " + divs + " divisions");
            return 0;
        }
        else
        {
            return 1 + floorLg(n/2, count+1, adds+1, divs+1);
        }
    }
}
