import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UnSubComponentsPage, UnSubDeleteDialog, UnSubUpdatePage } from './un-sub.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('UnSub e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let unSubComponentsPage: UnSubComponentsPage;
  let unSubUpdatePage: UnSubUpdatePage;
  let unSubDeleteDialog: UnSubDeleteDialog;
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

  it('should load UnSubs', async () => {
    await navBarPage.goToEntity('un-sub');
    unSubComponentsPage = new UnSubComponentsPage();
    await browser.wait(ec.visibilityOf(unSubComponentsPage.title), 5000);
    expect(await unSubComponentsPage.getTitle()).to.eq('cftcApp3App.unSub.home.title');
    await browser.wait(ec.or(ec.visibilityOf(unSubComponentsPage.entities), ec.visibilityOf(unSubComponentsPage.noResult)), 1000);
  });

  it('should load create UnSub page', async () => {
    await unSubComponentsPage.clickOnCreateButton();
    unSubUpdatePage = new UnSubUpdatePage();
    expect(await unSubUpdatePage.getPageTitle()).to.eq('cftcApp3App.unSub.home.createOrEditLabel');
    await unSubUpdatePage.cancel();
  });

  it('should create and save UnSubs', async () => {
    const nbButtonsBeforeCreate = await unSubComponentsPage.countDeleteButtons();

    await unSubComponentsPage.clickOnCreateButton();

    await promise.all([
      unSubUpdatePage.theRoleSelectLastOption(),
      unSubUpdatePage.setImageInput(absolutePath),
      unSubUpdatePage.titleFrSelectLastOption(),
      unSubUpdatePage.titleEnSelectLastOption(),
      unSubUpdatePage.descriptionFrSelectLastOption(),
      unSubUpdatePage.descriptionEnSelectLastOption(),
    ]);

    expect(await unSubUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);

    await unSubUpdatePage.save();
    expect(await unSubUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await unSubComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UnSub', async () => {
    const nbButtonsBeforeDelete = await unSubComponentsPage.countDeleteButtons();
    await unSubComponentsPage.clickOnLastDeleteButton();

    unSubDeleteDialog = new UnSubDeleteDialog();
    expect(await unSubDeleteDialog.getDialogTitle()).to.eq('cftcApp3App.unSub.delete.question');
    await unSubDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(unSubComponentsPage.title), 5000);

    expect(await unSubComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
