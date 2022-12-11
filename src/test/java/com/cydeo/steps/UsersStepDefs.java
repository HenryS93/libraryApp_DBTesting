package com.cydeo.steps;

import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.*;
import io.cucumber.java.eo.Se;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

public class UsersStepDefs {

    UsersPage usersPage = new UsersPage();
    String email;
    String expectedStatus;
    String activeStatus;



    @When("the user clicks Edit User button")
    public void the_user_clicks_edit_user_button() {

    usersPage.editUser.click();

    }


    @When("the user changes user status {string} to {string}")
    public void the_user_changes_user_status_to(String active, String inactive) {
//        Select select = new Select(usersPage.userStatusDropdown);
//        select.selectByVisibleText(inactive);

        BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown,inactive);

        email = usersPage.email.getAttribute("value");
        System.out.println("email= " + email);

        BrowserUtil.waitFor(2);
        expectedStatus=inactive;
        this.activeStatus=active;
    }


    @When("the user clicks save changes button")
    public void the_user_clicks_save_changes_button() {

    usersPage.saveChanges.click();
        System.out.println("---------> Users " +email+" is Deactivated");

    }


    @Then("{string} message should appear")
    public void message_should_appear(String expectedMsg) {
        // maybe this message will appear dynamically In that case we need to handle time issue with Explicit Wait
        BrowserUtil.waitFor(1);
        String actualMsg = usersPage.toastMessage.getText();
        Assert.assertEquals(expectedMsg,actualMsg);

    }


    @Then("the users should see same status for related user in database")
    public void the_users_should_see_same_status_for_related_user_in_database() {

        DB_Util.runQuery("select status from users\n" +
                "where email ='"+email+"'");

        //Get Data
        String actualStatus = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(expectedStatus,actualStatus);


    }


    @Then("the user changes current user status {string} to {string}")
    public void the_user_changes_current_user_status_to(String inactive, String active) {
    BrowserUtil.waitFor(1);
    BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown,inactive);

    BrowserUtil.waitFor(1);
    usersPage.editUser(email).click();

    }




}
