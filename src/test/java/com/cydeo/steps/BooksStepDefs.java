package com.cydeo.steps;

import com.cydeo.pages.BookPage;
import com.cydeo.pages.DashBoardPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.util.List;
import java.util.Map;

public class BooksStepDefs {
    BookPage bookPage=new BookPage();
    List<String> actualCategoryList;


    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        new DashBoardPage().navigateModule(moduleName);


    }

    @When("the user gets all book categories in webpage")
    public void the_user_gets_all_book_categories_in_webpage() {

        actualCategoryList=BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);
        System.out.println("actualCategoryList = " + actualCategoryList);
    }

    @Then("user should be able to see following categories")
    public void user_should_be_able_to_see_following_categories(List<String> expectedCategoryList) {


        Assert.assertEquals(expectedCategoryList, actualCategoryList);

    }


    @When("I open book {string}")
    public void i_open_book(String bookName) {

        System.out.println("bookName = " + bookName);
        BrowserUtil.waitForClickablility(bookPage.search, 5).sendKeys(bookName);
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();

    }

    @Then("verify book categories must match book categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {

         String query="select name from book_categories";

         //run query to get all categories from Database
        DB_Util.runQuery(query);

        //store data
       List<String> expectedCategoryList= DB_Util.getColumnDataAsList(1);


       // Assertions
        Assert.assertEquals(expectedCategoryList,actualCategoryList);


    }

    @Then("book information must match the database for {string}")
    public void book_information_must_match_the_database_for(String bookName) {


        BrowserUtil.waitFor(2);
        System.out.println(bookPage.bookName.getText());
        System.out.println("getAttribute(Value) --> "+ bookPage.bookName.getAttribute("value"));
        System.out.println("bookPage.bookName.getAttribute(\"innerHTML\")= " + bookPage.bookName.getAttribute("innerHTML"));
        /*
        1. getText() --> it will return text from provided element
        2. getAttribute("Value") --> if there is a input box we are gonna use getAttribute("value") to get data from here
        3. getAttribute("Inner HTML") --> it will get HTML code for related element
         */
        //Get Data from UI
        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthorName = bookPage.author.getAttribute("value");

        // get data from Database
        bookPage.isbn.getAttribute("value");
        bookPage.year.getAttribute("value");
        bookPage.description.getAttribute("value");
        





        //compare






    }

}
