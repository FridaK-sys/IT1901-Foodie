package ui;

import core.Cookbook;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ui.utils.CookbookAccess;

/**
 * Abstract class for RestApp- and LocalAppController.
 */
public abstract class AbstractController implements FoodieController, Initializable {

  protected CookbookAccess dataAccess;

  @FXML
  ListViewController mainListViewController;

  protected abstract void setUpStorage();

  public void setCookbookAccess(CookbookAccess access) {
    this.dataAccess = access;
    initializeRecipesView();
  }

  public void initializeRecipesView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }

  public Cookbook getCookbook() {
    return mainListViewController.getCookbook();
  }

}
