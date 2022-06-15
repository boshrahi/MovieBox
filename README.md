# Branch Master
This branch contains all codes and tests. All pages are in XML. 
![Project Screenshots](https://github.com/boshrahi/MovieBox/blob/850950f25eab78bbdce896c06cec095dc1eed320/screenshots/movie_box.png)

## Implementation Approach

My approach to developing the app was as follows:

1. Choosing clean architecture including data, domain and framework layers.

2. Setting up Hilt for dependency injection, which is more straightforward and has less boilerplate code than dagger2

3. Setting up network modules and dependencies such as Retrofit, OkhttpClient, Interceptors + Gson for parsing JSON. I have experience using `moshi` and `kotlinx` as parsers. However, both have some limitations. `kotlinx` does not correctly work with Generic type data models, and `moshi` has incompatibility with kotlin 1.6 and higher, which can cause problems. So using Gson is reasonable.

4. Creating SplashScreen using Launcher Theme since it is better than using Timers. Instead of creating a Layout that is an overhead for SplashScreen.

5. Starting from MainActivity with two empty fragments and bottom navigation. Using jetpack navigation to connect these two parts.

6. For implementing the Playing Now/Most popular page, this order:
    * implement data layer
    * implement domain layer
    * implement framework and datasourceImpl
    * implement presentation layer : fragments + ViewModel

7. `RatingView` is a simple custom view that gets only a percentage and, based on that, changes the color of the circle to green or red. Backgrounds have a little gradient and are stored in drawable. If the percentage is null, it will hide the views. Also, you can send the custom text color through the attribute `percentageTextColor` in the XML file.

8. On Playing Now page, some data were not available to show as it was illustrated in prototypes, such as `duration` and `rating,` because playing_now API does not have this information, and we cannot call detailed API for each of movies in the page. As a result, I have taken these steps:
    * omit duration from the Playing now page
    * For rating, I have used `vote_average* 10` to show the popularity percentage.

9. In Most Popular page I have used ViewPager2 due to below reasons:
    * Based on RecyclerView
    * RTL (right-to-left) layout support
    * Having `pageTransformer` easily

10. Unit Test are also available in test folder. They cover different levels of application.
    * domain
    * data
    * presentation
    * factory : class for mocking our data

## Third Party libraries

1. Glide

I have used' Glide' to load images in lists and detail pages. Although it has an extensive library compared to `Picasso` and `Fresco`, it performs well in this application that depicts many good quality images. Glide has a small cache and handles releasing memory (of the bitmap) when activity or fragment is destroyed. Moreover, Glide resizes images to fit the target dimensions to achieve optimal memory.

2. [ReadMore TextView](https://github.com/PRNDcompany/ReadMoreTextView)

It is a very simple and small library, entirely written in kotlin. Other libraries with even more stars actually did not perform well and had issues in my test cases.
