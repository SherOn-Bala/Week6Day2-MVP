## Model-View-Presenter ##

### Screnshots ###
<p float="left">
  <img src="/screenshots/1.jpg" width="200" />
  <img src="/screenshots/2.jpg" width="200" />
  <img src="/screenshots/3.jpg" width="200" />
  <img src="/screenshots/4.jpg" width="200" />
</p>

### Coding ###
#### Refactor an old homework with an API call or using a database (preferably both) using MVP ####

### Research ###
1. <b>What is the difference between Dagger 1 and Dagger 2</b>
* Major differences in Dagger 2 compared to Dagger 1 are:
    * Relies on [JSR 330](https://jcp.org/en/jsr/detail?id=330) for declaring injection sites.
    * All types of injection supported by Dagger 1 continue to be supported, along with method injection.
    * Static injection not supported.
    * Configuration complexity for modules reduced.
    * Graph validation no longer performed on a per-module basis.
    * No more reflection.
    * Allows users to use any well-formed scope annotation.
2. <b>What are the custom scopes in Dagger?</b>
    * A scope where we choose how the scoped objects work with the lifecycle.
3. <b>What are the advantages of MVP over MVC?</b>
    * MVC data and event-flow is circular where the view also contains logic and makes the system difficult ot test and hard to maintain.
    * MVP replaces controller with presenter which mediates between the view and the model making the system linear.
    * Logic moved to presenter.
    * Since the UI is isolated from the app logic, they can be developed independently.
4. <b>Define:</b>
    1. @Inject
        * Used to request dependencies.
        * Can be used on a constructor, field or method.
    2. @Component
        * Enables selected modules and used for performing dependency injection.
        * Used on an interface used by Dagger 2 to generate code which uses the modules to fulfill the requested dependencies.
    3. @Providers
        * Defines classes and methods which provide dependencies.
        * Can be used on methods in classes annotated with @Module and is used for methods which provides objects for dependencies injection.
    4. @Module
        * Defines classes and methods which provide dependencies.
        * Used on classes which contains methods annotated with @Provides.