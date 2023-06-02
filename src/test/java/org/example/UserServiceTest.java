package org.example;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*; //This is a static import, thus we do not need to call Mockito.xyz method everytime.

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    int POSITIVE_CASE = 1;
    int NEGATIVE_CASE = 2;
    int EDGE_CASE = 3;

    @Mock
    private Map<String, User> userDatabase;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        System.out.println("Starting tests for method with @Before");
    }

    @BeforeClass
    public static void setClass() {
        System.out.println("Setting up @BeforeClass");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Compiling class after testing with @AfterClass");
    }

    @After
    public void tearDown() {
        System.out.println("Executing clean up operations after each test with @After");
    }

    private User assembleUser(int type) {
        if (POSITIVE_CASE == type) {
            return new User("John", "johnpw", "john@gmail.com");

        } else if (NEGATIVE_CASE == type) {
            return new User("Will", "willpw", "will@gmail.com");

        } else if (EDGE_CASE == type) {
            return new User("", "password", "edge@gmail.com");
        }
        return null;
    }

    //test register user()
    @Test
    public void registerUserPositive() {
        User user = assembleUser(POSITIVE_CASE);
        when(userDatabase.containsKey("John")).thenReturn(false);  //set up mock behavior for existing username in database, return false
        boolean isRegistered = userService.registerUser(user);    //registering user
        assertTrue(isRegistered); //verify result comes back true
        verify(userDatabase).put("John", user);    //verify that the 'put' method of 'userDatabase' is called with the expected arguments

    }


    @Test
    public void registerUserNegative() {
        User user = assembleUser(NEGATIVE_CASE);
        when(userDatabase.containsKey("Will")).thenReturn(true);   //set up mock behavior for existing username in database, return true
        boolean isRegistered = userService.registerUser(user);    //registering user
        assertFalse(isRegistered);    //verify result comes back false
        verify(userDatabase, times(0)).put(anyString(), any(User.class));    //verify that 'puy' of 'userDatabase' is never called

    }

    // Edge case test for registerUser()
    @Test
    public void registerUserEdgeCase() {
        User user = assembleUser(EDGE_CASE);
        when(userDatabase.containsKey("")).thenReturn(false);
        boolean isRegistered = userService.registerUser(user);
        assertTrue(isRegistered);
        verify(userDatabase).put("", user);
    }
    // Positive test case for loginUser()
    @Test
    public void loginUserPositive() {
        User user = assembleUser(POSITIVE_CASE);
        when(userDatabase.containsKey("John")).thenReturn(true);
        when(userDatabase.get("John")).thenReturn(user);
        User loggedInUser = userService.loginUser("John", "johnpw");
        assertNotNull(loggedInUser);
        assertEquals(user, loggedInUser);
    }

    // Negative test case for loginUser()
    @Test
    public void loginUserNegative() {
        User user = assembleUser(NEGATIVE_CASE);
        when(userDatabase.containsKey("Will")).thenReturn(false);
        User loggedInUser = userService.loginUser("Will", "willpw");
        assertNull(loggedInUser);
    }

    // Edge case test for loginUser()
    @Test
    public void loginUserEdgeCase() {
        User user = assembleUser(EDGE_CASE);
        when(userDatabase.containsKey("")).thenReturn(false);
        User loggedInUser = userService.loginUser("", "password");
        assertNull(loggedInUser);
    }

}

//    @Test
//    public void testRegisterUserNegative() {
//        User existingUser = new User("existingUser", "password", "email");
//        Mockito.when(userDatabase.containsKey(existingUser.getUsername())).thenReturn(true);
//
//        assertFalse(userService.registerUser(existingUser));
//    }
//
//    @Test
//    public void testRegisterUserEdgeCase() {
//        User nullUser = null;
//        Assertions.assertThrows(NullPointerException.class, () -> {
//            userService.registerUser(nullUser);
//        });
//    }























//
//
//
//private UserService userService; // instantiate as a test double, its not real instance but mock version
////    userDatabase = Mockito.mock(Map.class); // Use Map instead of HashMap
//
//@BeforeEach
//public void setUp() {
//        userService = mock(UserService.class);
////        UserService = new UserService(UserService);
//
//        }
//
//@AfterEach
//public void tearDown() {
//        userService = null;
//        userDatabase = null;
//        }
//
//@Test
//public void testRegisterUserPositive() {
//        User newUser = new User("username", "password", "email");
//        Mockito.when(userDatabase.containsKey(newUser.getUsername())).thenReturn(false);
//
//        assertTrue(userService.registerUser(newUser));





















//
//    @Test // positive test
//    public void RegisterUserPositiveCase() {
//        UserService userService = new UserService();
//        User user = new User("JohnDoe", "password123", "john.doe@example.com");
//        assertTrue(userService.registerUser(user), "User registration passed.");
//    }
//
//    @Test
//    public void RegisterUserNegativeCase() {
//        UserService userService = new UserService();
//        User existingUser = new User("SameUsername", "pleasedontlook123", "same.username@example.com"); // Create a user with a duplicate username
//        userService.registerUser(existingUser);
//        User newUser = new User("SameUsername", "newpassword", "notthesameemail@example.com"); // Attempt to register a new user with the same username
//        assertFalse(userService.registerUser(newUser));
//    }
//
//    @Test
//    public void testRegisterUser_Edge() {
//        UserService userService = new UserService();
//        User user = new User("", "password", "john@example.com");
//        assertTrue(userService.registerUser(user), "User registration with empty username passed.");
//
//











//    @Test
//    public void testRegisterUser_Edge() {
//        UserService userService = new UserService();
//        // Create a user with the maximum allowed username length
//        String maxUsername = "x".repeat(255); // 255 characters
//        User user = new User(maxUsername, "password123", "user@example.com");
//        boolean registrationStatus = userService.registerUser(user);
//
//        assertTrue(registrationStatus);
//    }












//
//    private UserService userService;
//    private Map<String, User> userDatabase;
//
//    @Before
//    public void setUp() {
//        userService = new UserService();
//        userDatabase = new HashMap<>();
////        userService.setUserDatabase(userDatabase);
//    }
//
//    @Test
//    public void testRegisterUser_Positive() {
//        // Create a new user
//        User user = new User("john", "password", "john@example.com");
//
//        // Register the user
//        boolean result = userService.registerUser(user);
//
//        // Assert that the user was registered successfully
//        assertTrue(result);
//        assertTrue(userDatabase.containsKey("john"));
//        assertEquals(user, userDatabase.get("john"));
//    }












//
//    private UserService userService;
//    private User mockUser;
//
//    @Before
//    public void setUp() {
//        userService = new UserService();
//        mockUser = Mockito.mock(User.class);
//    }
//
//    @After
//    public void tearDown() {
//        userService = null;
//        mockUser = null;
//    }
//
//    @Test
//    public void testRegisterUser_Positive() {
//        // Create a new user
//        User user = new User("johnDoe", "password", "john@example.com");
//
//        // Register the user
//        boolean result = userService.registerUser(user);
//
//        // Verify the user is registered successfully
//        assertTrue(result);
//    }