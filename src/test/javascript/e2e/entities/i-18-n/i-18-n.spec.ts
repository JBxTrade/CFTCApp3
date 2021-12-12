import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { I18nComponentsPage, I18nDeleteDialog, I18nUpdatePage } from './i-18-n.page-object';

const expect = chai.expect;

describe('I18n e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let i18nComponentsPage: I18nComponentsPage;
  let i18nUpdatePage: I18nUpdatePage;
  let i18nDeleteDialog: I18nDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load I18ns', async () => {
    await navBarPage.goToEntity('i-18-n');
    i18nComponentsPage = new I18nComponentsPage();
    await browser.wait(ec.visibilityOf(i18nComponentsPage.title), 5000);
    expect(await i18nComponentsPage.getTitle()).to.eq('cftcApp3App.i18n.home.title');
    await browser.wait(ec.or(ec.visibilityOf(i18nComponentsPage.entities), ec.visibilityOf(i18nComponentsPage.noResult)), 1000);
  });

  it('should load create I18n page', async () => {
    await i18nComponentsPage.clickOnCreateButton();
    i18nUpdatePage = new I18nUpdatePage();
    expect(await i18nUpdatePage.getPageTitle()).to.eq('cftcApp3App.i18n.home.createOrEditLabel');
    await i18nUpdatePage.cancel();
  });

  it('should create and save I18ns', async () => {
    const nbButtonsBeforeCreate = await i18nComponentsPage.countDeleteButtons();

    await i18nComponentsPage.clickOnCreateButton();

    await promise.all([i18nUpdatePage.setFrInput('fr'), i18nUpdatePage.setEnInput('en')]);

    expect(await i18nUpdatePage.getFrInput()).to.eq('fr', 'Expected Fr value to be equals to fr');
    expect(await i18nUpdatePage.getEnInput()).to.eq('en', 'Expected En value to be equals to en');

    await i18nUpdatePage.save();
    expect(await i18nUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await i18nComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last I18n', async () => {
    const nbButtonsBeforeDelete = await i18nComponentsPage.countDeleteButtons();
    await i18nComponentsPage.clickOnLastDeleteButton();

    i18nDeleteDialog = new I18nDeleteDialog();
    expect(await i18nDeleteDialog.getDialogTitle()).to.eq('cftcApp3App.i18n.delete.question');
    await i18nDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(i18nComponentsPage.title), 5000);

    expect(await i18nComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
