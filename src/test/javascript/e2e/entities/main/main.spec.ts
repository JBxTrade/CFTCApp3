import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MainComponentsPage, MainDeleteDialog, MainUpdatePage } from './main.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Main e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let mainComponentsPage: MainComponentsPage;
  let mainUpdatePage: MainUpdatePage;
  let mainDeleteDialog: MainDeleteDialog;
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

  it('should load Mains', async () => {
    await navBarPage.goToEntity('main');
    mainComponentsPage = new MainComponentsPage();
    await browser.wait(ec.visibilityOf(mainComponentsPage.title), 5000);
    expect(await mainComponentsPage.getTitle()).to.eq('cftcApp3App.main.home.title');
    await browser.wait(ec.or(ec.visibilityOf(mainComponentsPage.entities), ec.visibilityOf(mainComponentsPage.noResult)), 1000);
  });

  it('should load create Main page', async () => {
    await mainComponentsPage.clickOnCreateButton();
    mainUpdatePage = new MainUpdatePage();
    expect(await mainUpdatePage.getPageTitle()).to.eq('cftcApp3App.main.home.createOrEditLabel');
    await mainUpdatePage.cancel();
  });

  it('should create and save Mains', async () => {
    const nbButtonsBeforeCreate = await mainComponentsPage.countDeleteButtons();

    await mainComponentsPage.clickOnCreateButton();

    await promise.all([
      mainUpdatePage.theRoleSelectLastOption(),
      mainUpdatePage.setImageInput(absolutePath),
      mainUpdatePage.subLinkSelectLastOption(),
      mainUpdatePage.linkSelectLastOption(),
      mainUpdatePage.linkDataSelectLastOption(),
      mainUpdatePage.titleFrSelectLastOption(),
      mainUpdatePage.titleEnSelectLastOption(),
      mainUpdatePage.descriptionFrSelectLastOption(),
      mainUpdatePage.descriptionEnSelectLastOption(),
    ]);

    expect(await mainUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);

    await mainUpdatePage.save();
    expect(await mainUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await mainComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Main', async () => {
    const nbButtonsBeforeDelete = await mainComponentsPage.countDeleteButtons();
    await mainComponentsPage.clickOnLastDeleteButton();

    mainDeleteDialog = new MainDeleteDialog();
    expect(await mainDeleteDialog.getDialogTitle()).to.eq('cftcApp3App.main.delete.question');
    await mainDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(mainComponentsPage.title), 5000);

    expect(await mainComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
