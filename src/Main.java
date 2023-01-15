import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) throws java.lang.NullPointerException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]),
                Input.class);

        Platform platform = new Platform();
        platform.setState("Homepage NoLog");
        User user = new User();
        CurrentUser currentUser = new CurrentUser(user);
        int to_add = 0;
        Movie movie = new Movie();

        platform.SetPlatformMovies(inputData.getMovies());
        platform.SetRegisteredUsers(inputData.getUsers());

        for (CurrentUser currentUser1 : platform.getRegisteredUsers()) {
            UserSubs subs = new UserSubs(currentUser1);
            platform.getUserSubs().add(subs);
            UserRatings ratings = new UserRatings(currentUser1);
            platform.getUserRatings().add(ratings);
        }

        ArrayList<Output> output = new ArrayList<Output>();

        for (Action action : inputData.getActions()) {
            System.out.println(platform.getState());
            System.out.printf("nr actiune: ");
            System.out.println(inputData.getActions().indexOf(action) + 1);
            System.out.printf("pozitie output: ");
            System.out.println(output.size() + 1);
            if (action.getType().equals("back") && platform.getState().equals("Details")) {
                platform.setState("Movies");
                platform.getListedMovies().clear();
                ActionHandler.ListMovies(platform);
                if (platform.getListedMovies().isEmpty()) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
            }
            else if (action.getType().equals("back") && platform.getState().equals("Movies")) {
                platform.getListedMovies().clear();
                platform.setState("Homepage Log");
            }
            else if (action.getType().equals("back") && !(platform.getState().equals("Movies") || platform.getState().equals("Details"))) {
                output.add(new Output("Error", platform.getListedMovies(), null));
            }


            if (action.getType().equals("change page")) {
                if (action.getPage().equals(("logout")) && platform.getState().equals("Homepage NoLog"))
                    output.add(new Output("Error", platform.getListedMovies(), null));
                if (action.getPage().equals("logout")) ActionHandler.Logout(platform);


                if (action.getPage().equals("login") && !platform.getState().equals("Homepage NoLog")) {
                    if (!platform.getState().equals("Homepage Log")) {
                        ActionHandler.Logout(platform);
                    }
                    output.add(new Output("Error", platform.getListedMovies(), null));

                }
                else if (action.getPage().equals("login") && platform.getState().equals("Homepage NoLog")) {
                    platform.setState("Login");
                }


                if (action.getPage().equals("register") && !platform.getState().equals("Homepage NoLog")) {
                    ActionHandler.Logout(platform);
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getPage().equals("register") && platform.getState().equals("Homepage NoLog")) {
                    platform.setState("Register");
                }


                if (action.getPage().equals("movies") && !(platform.getState().equals("Homepage Log") || platform.getState().equals("Upgrades") || platform.getState().equals("Details"))) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getPage().equals("movies") && (platform.getState().equals("Homepage Log") || platform.getState().equals("Upgrades") || platform.getState().equals("Details"))) {
                    platform.setState("Movies");
                    if (to_add == 1) {
                        to_add = 0;
                        if (!ActionHandler.Add(platform, movie))
                            output.add(new Output("Error", new ArrayList<MovieRun>(), null));
                    }
                        platform.getListedMovies().clear();
                        ActionHandler.ListMovies(platform);
                    output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }


                if (action.getPage().equals("see details") && !(platform.getState().equals("Movies") || (platform.getState().equals("Details")))) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getPage().equals("see details") && (platform.getState().equals("Movies") || (platform.getState().equals("Details")))) {
                    if (platform.getState().equals("Details"))
                        ActionHandler.ListMovies(platform);
                    ActionHandler.SeeDetails(platform, action.getMovie());
                    if (platform.getListedMovies().size() == 0) {
                        output.add(new Output("Error", platform.getListedMovies(), null));
                        platform.setState("Movies");
                    }
                    else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                    platform.setState("Details");
                }


                if (action.getPage().equals("upgrades") && (platform.getState().equals("Homepage Log") || platform.getState().equals("Details"))) {
                    platform.setState("Upgrades");
                }
                else if (action.getPage().equals("upgrades") && !(platform.getState().equals("Homepage Log") || platform.getState().equals("Details"))) {
                    ActionHandler.Logout(platform);
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }


            }
            if (action.getType().equals("on page")) {

                if (action.getFeature().equals("login") && (platform.getState().equals("Homepage NoLog") || platform.getState().equals("Register"))) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                    platform.setState("Homepage NoLog");
                }
                else if (action.getFeature().equals("login") && !platform.getState().equals("Login")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("login") && platform.getState().equals("Login")) {
                    if (!ActionHandler.Login(platform, action.getCredentials())) {
                        platform.setState("Homepage NoLog");
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                    else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }


                if (action.getFeature().equals("register") && (platform.getState().equals("Homepage NoLog") || platform.getState().equals("Login"))) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                    platform.setState("Homepage NoLog");
                }
                else if (action.getFeature().equals("register") && !platform.getState().equals("Register")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("register") && platform.getState().equals("Register")) {
                    if (!ActionHandler.Register(platform, action.getCredentials())) {
                        output.add(new Output("Error", platform.getListedMovies(), null));
                        platform.setState("Homepage NoLog");
                    } else {
                        output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                    }
                }


                if (action.getFeature().equals("search") && !(platform.getState().equals("Movies"))) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("search") && platform.getState().equals("Movies")) {
                    platform.getListedMovies().clear();
                    ActionHandler.ListMovies(platform);
                    ActionHandler.SearchMovies(platform, action.getStartsWith());
                    output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }


                if (action.getFeature().equals("filter") && !(platform.getState().equals("Movies") || platform.getState().equals("Details"))) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("filter") && (platform.getState().equals("Movies") || platform.getState().equals("Details"))) {
                    platform.getListedMovies().clear();
                    ActionHandler.ListMovies(platform);
                    ActionHandler.FilterMovies(platform, action.getFilters());
                    output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }


                if (action.getFeature().equals("buy tokens") && !platform.getState().equals("Upgrades")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("buy tokens") && platform.getState().equals("Upgrades")) {
                    if (!ActionHandler.BuyTokens(platform, action.getCount())) {
                        ActionHandler.Logout(platform);
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                }


                if (action.getFeature().equals("buy premium account") && !platform.getState().equals("Upgrades")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("buy premium account") && platform.getState().equals("Upgrades")) {
                    if (!ActionHandler.BuyPremiumAccount(platform)) {
                        ActionHandler.Logout(platform);
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                }


                if (action.getFeature().equals("purchase") && !platform.getState().equals("Details")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("purchase") && platform.getState().equals("Details")) {
                    if (!ActionHandler.PurchaseMovie(platform, action.getMovie())) {
                        platform.setState("Details");
                        output.add(new Output("Error", new ArrayList<MovieRun>(0), null));
                    }
                    else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }


                if (action.getFeature().equals("watch") && !platform.getState().equals("Details")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("watch") && platform.getState().equals("Details")) {
                    if (!ActionHandler.WatchMovie(platform, action.getMovie())) {
                        if (!platform.getListedMovies().isEmpty()) {
                            platform.setState("Homepage Log");
                            platform.getListedMovies().clear();
                        }
                        else platform.setState("Details");
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                    else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }


                if (action.getFeature().equals("like") && !platform.getState().equals("Details")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("like") && platform.getState().equals("Details")) {
                    if (!ActionHandler.LikeMovie(platform, action.getMovie())) {
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                    else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }

                if (action.getFeature().equals("rate") && !platform.getState().equals("Details")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("rate") && platform.getState().equals("Details")) {
                    if (!ActionHandler.RateMovie(platform, action.getRate())) {
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                    else output.add(new Output(null , platform.getListedMovies(), platform.getCurrentUser()));
                }

                if (action.getFeature().equals("subscribe") && !platform.getState().equals("Details")) {
                    output.add(new Output("Error", platform.getListedMovies(), null));
                }
                else if (action.getFeature().equals("subscribe") && platform.getState().equals("Details")) {
                    if (!ActionHandler.Subscribe(platform, action.getSubscribedGenre())) {
                        platform.setState("Homepage Log");
                        platform.getListedMovies().clear();
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                    else if (platform.getListedMovies().isEmpty()) {
                        output.add(new Output("Error", platform.getListedMovies(), null));
                    }
                }
            }


            if (action.getType().equals("database")) {
                if (action.getFeature().equals("add")) {
                    to_add = 1;
                    movie = action.getAddedMovie();
                    System.out.println(movie.toString());
                }
            }


            System.out.println(platform.getCurrentUser());
            System.out.println();
        }
        if (platform.getCurrentUser() != null) {
            if (platform.getCurrentUser().getCredentials().getAccountType().equals("premium")){
                ActionHandler.Recommend(platform);
                output.add(new Output(null , null, platform.getCurrentUser()));
        }

        }
        //for (UserRatings userRatings : platform.getUserRatings()) {
        //    System.out.println(userRatings.getUser());
        //    for (Ratings ratings : userRatings.getRatings()) {
        //        System.out.println(ratings.getMovieName());
        //        System.out.println(ratings.getRate());
        //   }
        //    System.out.println();
        //}
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
