import navigator.Navigator;
import navigator.NavigatorImpl;
import route.Route;
import structure.list.ArrayList;

import java.util.Arrays;
import java.util.Scanner;


public class Main {
    private static final Navigator navigator = new NavigatorImpl();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        addSampleRoutes();

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addRoute();
                case 2 -> removeRoute();
                case 3 -> searchRoutes();
                case 4 -> getFavoriteRoutes();
                case 5 -> getTop3Routes();
                case 6 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Navigator Console App Menu:");
        System.out.println("1. Add Route");
        System.out.println("2. Remove Route");
        System.out.println("3. Search Routes");
        System.out.println("4. Get Favorite Routes");
        System.out.println("5. Get Top 3 Routes");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addRoute() {
        System.out.println("Enter Route details:");

        System.out.print("ID: ");
        String id = scanner.next();

        System.out.print("Distance: ");
        double distance = scanner.nextDouble();

        System.out.print("Popularity: ");
        int popularity = scanner.nextInt();

        System.out.print("Is Favorite (true/false): ");
        boolean isFavorite = scanner.nextBoolean();

        System.out.print("Number of Location Points: ");
        int numPoints = scanner.nextInt();

        String[] locationPoints = new String[numPoints];
        for (int i = 0; i < numPoints; i++) {
            System.out.print("Enter Location Point " + (i + 1) + ": ");
            locationPoints[i] = scanner.next();
        }


        navigator.addRoute(createRoute(id,distance,popularity,isFavorite,locationPoints));
        System.out.println("Route added successfully!");
    }

    private static void removeRoute() {
        System.out.print("Enter Route ID to remove: ");
        String routeId = scanner.next();
        navigator.removeRoute(routeId);
        System.out.println("Route removed successfully!");
    }

    private static void searchRoutes() {
        System.out.print("Enter Start Point: ");
        String startPoint = scanner.next();

        System.out.print("Enter End Point: ");
        String endPoint = scanner.next();

        Iterable<Route> result = navigator.searchRoutes(startPoint, endPoint);
        printRoutes("Search Result", result);
    }

    private static void getFavoriteRoutes() {
        System.out.print("Enter Destination Point: ");
        String destinationPoint = scanner.next();

        Iterable<Route> result = navigator.getFavoriteRoutes(destinationPoint);
        printRoutes("Favorite Routes", result);
    }

    private static void getTop3Routes() {
        Iterable<Route> result = navigator.getTop3Routes();
        printRoutes("Top 3 Routes", result);
    }


    private static void printRoutes(String title, Iterable<Route> routes) {
        System.out.println(title + ":");
        for (Route route : routes) {
            System.out.println(route);
        }
        System.out.println();
    }
    private static void addSampleRoutes() {
        Route route1 = createRoute("1", 100.0, 3, true, "A", "B", "C");
        Route route2 = createRoute("2", 150.0, 2, false, "A", "D");
        Route route3 = createRoute("3", 120.0, 4, true, "B", "C", "D", "E");

        navigator.addRoute(route1);
        navigator.addRoute(route2);
        navigator.addRoute(route3);
    }
    private static Route createRoute(String id, double distance, int popularity, boolean isFavorite, String... locationPoints) {
        Route route = new Route();
        route.setId(id);
        route.setDistance(distance);
        route.setPopularity(popularity);
        route.setFavorite(isFavorite);
        route.setLocationPoints(new ArrayList<>(Arrays.asList(locationPoints)));
        return route;
    }
}