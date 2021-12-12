import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SubComponentsPage, SubDeleteDialog, SubUpdatePage } from './sub.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Sub e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let subComponentsPage: SubComponentsPage;
  let subUpdatePage: SubUpdatePage;
  let subDeleteDialog: SubDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Subs', async () => {
    await navBarPage.goToEntity('sub');
    subComponentsPage = new SubComponentsPage();
    await browser.wait(ec.visibilityOf(subComponentsPage.title), 5000);
    expect(await subComponentsPage.getTitle()).to.eq('cftcApp3App.sub.home.title');
    await browser.wait(ec.or(ec.visibilityOf(subComponentsPage.entities), ec.visibilityOf(subComponentsPage.noResult)), 1000);
  });

  it('should load create Sub page', async () => {
    await subComponentsPage.clickOnCreateButton();
    subUpdatePage = new SubUpdatePage();
    expect(await subUpdatePage.getPageTitle()).to.eq('cftcApp3App.sub.home.createOrEditLabel');
    await subUpdatePage.cancel();
  });

  it('should create and save Subs', async () => {
    const nbButtonsBeforeCreate = await subComponentsPage.countDeleteButtons();

    await subComponentsPage.clickOnCreateButton();

    await promise.all([
      subUpdatePage.theRoleSelectLastOption(),
      subUpdatePage.setImageInput(absolutePath),
      subUpdatePage.titleFrSelectLastOption(),
      subUpdatePage.titleEnSelectLastOption(),
      subUpdatePage.descriptionFrSelectLastOption(),
      subUpdatePage.descriptionEnSelectLastOption(),
      subUpdatePage.unSubSelectLastOption(),
    ]);

    expect(await subUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);

    await subUpdatePage.save();
    expect(await subUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await subComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Sub', async () => {
    const nbButtonsBeforeDelete = await subComponentsPage.countDeleteButtons();
    await subComponentsPage.clickOnLastDeleteButton();

    subDeleteDialog = new SubDeleteDialog();
    expect(await subDeleteDialog.getDialogTitle()).to.eq('cftcApp3App.sub.delete.question');
    await subDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(subComponentsPage.title), 5000);

    expect(await subComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
