# DaggerSharpener
[![bintray](https://api.bintray.com/packages/jeefo12/DaggerSharpener/daggersharpener-processor/images/download.svg) ](https://bintray.com/jeefo12/DaggerSharpener/daggersharpener-processor/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/AlexDochioiu/DaggerSharpener.svg?branch=master)](https://travis-ci.org/AlexDochioiu/DaggerSharpener)
[![codecov](https://codecov.io/gh/AlexDochioiu/DaggerSharpener/branch/master/graph/badge.svg)](https://codecov.io/gh/AlexDochioiu/DaggerSharpener)

**DaggerSharpener is an Android library which simplifies the creation of a Dagger2 dependency graph while helping mentain the code more readable. This library comes as an extension for Dagger2.**

*In this repository you can find an example project showing its usage as well as the library projects. The example is replicating the following dagger2 tutorial project (using Dagger2 + DaggerSharpener): https://github.com/patrick-doyle/dagger2-tutorial*

## Installation with Android Gradle
```groovy
// Add DaggerSharpener and Dagger2 dependencies
dependencies {
    implementation 'com.github.alexdochioiu:daggersharpener:0.1.0'
    annotationProcessor 'com.github.alexdochioiu:daggersharpener-processor:0.1.0'
    
    implementation 'com.google.dagger:dagger:2.17'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.17'
}
```

## Usage Example

### Step 1: Create a normal **dagger2** module

```groovy
@Module
public class MyModule {
    @Provides
    Object myObject() {
        return new Object();
    }
}
```
**Note: As you can see, no scope has yet been given to the object. This will be addressed in the following steps.**

### Step 2: Creating the Application (Sharp) Component ()

```groovy
@SharpComponent(
        modules = MyModule.class // array of multiple modules can be provided as {A.class, B.class, ...}
)
public class MyApplication extends Application {
}
```
#### Step 2.1: Build the project

After building, two files are generated: 

*SharpMyApplicationComponent.java* -- (Names is formed as: **Sharp** + Class Name + **Component**)
```groovy
@Component
@SharpMyApplicationScope
public interface SharpMyApplicationComponent {
  MyApplication inject(MyApplication thisClass);
}
```

*SharpMyApplicationScope.java* -- (Names is formed as: **Sharp** + Class Name + **Scope**)
```groovy
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SharpMyApplicationScope {}
```

#### Step 2.2 (Optional): Go back to the module and add the scope for the provided object(s)
```groovy
@Module
public class MyModule {
    @Provides
    @SharpMyApplicationScope
    Object myObject() {
        return new Object();
    }
}
```

#### Step 2.3 : Inject the dependencies in MyApplication
```groovy
@SharpComponent(
        modules = MyModule.class
)
public class MyApplication extends Application {

    private SharpMyApplicationComponent sharpComponent;
    
    @Inject
    Object injectedObject;

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Name is: Dagger + Sharp + Class Name + Component
        sharpComponent = DaggerSharpMyApplicationComponent.builder().build();
        sharpComponent.inject(this);

        Log.d("MyApplication", "onCreate: " + injectedObject.toString());
    }

    public SharpMyApplicationComponent getSharpComponent() {
        return sharpComponent;
    }
}
```

**Note: By running the code and checking logcat, you will see that the injectedObject is non-null. This indicates our object got successfully injected.**

### Step 3: Creating the Activity (Sharp) Component
```groovy
@SharpComponent(
        dependencies = SharpMyApplicationComponent.class
)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```
**Note: The new Sharp Activity Component depends on SharpMyApplicationComponent**

#### Step 3.1: Build the project

After building, two new files are generated: 

*SharpMainActivityComponent.java*
```groovy
@Component
@SharpMainActivityScope
public interface SharpMainActivityComponent {
  MainActivity inject(MainActivity thisClass);
}
```

*SharpMainActivityScope.java*
```groovy
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SharpMainActivityScope {}
```

#### Step 3.2: Expose dependencies from MyApplication to its dependants
```groovy
@SharpComponent(
        modules = MyModule.class,
        provides = Object.class // Array of dependencies can be provided as {A.class, B.class, etc.}
)
public class MyApplication extends Application {
  ...
}
```

#### Step 3.3 : Inject the dependencies in MainActivity
```groovy
@SharpComponent(
        sharpDependencies = MyApplication.class // The class(es) annotated with @SharpComponent
)
public class MainActivity extends AppCompatActivity {

    @Inject
    Object myObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerSharpMainActivityComponent.builder()
                .sharpMyApplicationComponent(((MyApplication) getApplication()).getSharpComponent())
                .build()
                .inject(this);

        Log.d("MainActivity", "onCreate: " + myObject.toString());
    }
}
```

**Note: By running the code and checking logcat, you will see that injectedObject from MyApplication and myObject from MainActivity are indeed the same instance (as long as you did the step 2.2). If you did not do step 2.2, the instance will be different for the two objects.**

### Step 4: Enjoy

Using DaggerSharpener **will still allow** you to use `@Inject` on constructors. Also, the generated scopes can be safely used on the classes which inject constructors.

For some docs, keep on reading below!

## SharpComponent explained

### modules
Takes an array of dagger2 modules

### sharpDependencies
Takes an array of classes **annotated as** `@SharpComponent` which will serve as dependencies for *this* new sharp component which is to be generated.

### dependencies
Takes an array of dagger2 components which will serve as dependencies for *this* new sharp component which is to be generated. This is to allow the developer to mix manually created dagger2 components and sharp components.
*Note: sharpDependencies and dependencies can be used together safely*

### scope
If you want to use a manually created scope, you can give it there. If you do so, no scope will be generated for this sharp component.

### provides
The array of (un-named) classes to be provided to the dependants of this sharp component

### providesNamed (to be released in version 0.2.0)
The array of `@NamedPair` entries. Each entry has a `String` name and a class (see below)

## SharpComponent example (slightly more complex than before)

```groovy
@SharpComponent(
        modules = {GithubServiceModule.class, PicassoModule.class},
        sharpDependencies = MyApplication.class,
        //dependencies = SomeDaggerComponent.class,
        scope = MyCustomScope.class,
        provides = {GithubService.class, Resources.class},
        providesNamed = {
                @NamedPair(aName = "myPicasso", aClass = Picasso.class),
                @NamedPair(aName = "appContext", aClass = Context.class),
                @NamedPair(aName = "activityContext", aClass = Context.class)
        }
)
public class MyFragment extends Fragment {
  ...
}
```

**The code above would generate the following component**

```groovy
@Component(
    modules = {GithubServiceModule.class, PicassoModule.class},
    dependencies = {SharpMyApplicationComponent.class}
)
@MyCustomScope
public interface SharpMyFragmentComponent {
  MyFragment inject(MyFragment thisClass);

  GithubService provideGithubService();

  Resources provideResources();

  @Named("myPicasso")
  Picasso providePicasso_myPicasso();

  @Named("appContext")
  Context provideContext_appContext();

  @Named("activityContext")
  Context provideContext_activityContext();
}
```
