package navigator;

import route.Route;
import structure.list.ArrayList;
import structure.list.List;
import structure.map.HashMap;
import structure.map.Map;

import java.util.Comparator;


public class NavigatorImpl implements Navigator {
    Map<String,Route> map = new HashMap<>();
    @Override
    public void addRoute(Route route) {
        map.put(route.getId(), route);
    }

    @Override
    public void removeRoute(String routeId) {
        map.remove(routeId);
    }

    @Override
    public boolean contains(Route route) {
        return map.containsValue(route);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Route getRoute(String routeId) {
        return map.get(routeId);
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = map.get(routeId);
        route.setPopularity(route.getPopularity() + 1);
        map.put(routeId, route);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> result = new ArrayList<>();

        for (Route route : map.values()) {
            List<String> points = route.getLocationPoints();
            if (points.size() >= 2 && points.contains(startPoint) && points.contains(endPoint)) {
                result.add(route);
            }
        }

        result.sort(Comparator.comparing(Route::isFavorite)
                .thenComparing(route -> {
                    int startPointIndex = route.getLocationPoints().indexOf(startPoint);
                    int endPointIndex = route.getLocationPoints().indexOf(endPoint);
                    if (endPointIndex >= startPointIndex) {
                        return endPointIndex - startPointIndex;
                    } else {
                        return startPointIndex - endPointIndex;
                    }
                }).reversed()
                .thenComparing(Route::getPopularity).reversed());
        return result;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> result = new ArrayList<>();

        for (Route route : map.values()) {
            List<String> points = route.getLocationPoints();
            if (route.isFavorite() && points.size() >= 2 && !points.get(0).equals(destinationPoint)) {
                result.add(route);
            }
        }
        result.sort(Comparator.comparing(Route::getDistance).thenComparing(Route::getPopularity).reversed());
        return result;
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        List<Route> allRoutes = new ArrayList<>(map.values());
        allRoutes.sort(Comparator.comparing(Route::getPopularity).reversed()
                .thenComparing(Route::getDistance)
                .thenComparing(route -> route.getLocationPoints().size()));

        return allRoutes.subList(0, Math.min(3, allRoutes.size()));
    }
}
