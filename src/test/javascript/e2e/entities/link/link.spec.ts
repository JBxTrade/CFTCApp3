import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LinkComponentsPage, LinkDeleteDialog, LinkUpdatePage } from './link.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Link e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let linkComponentsPage: LinkComponentsPage;
  let linkUpdatePage: LinkUpdatePage;
  let linkDeleteDialog: LinkDeleteDialog;
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

  it('should load Links', async () => {
    await navBarPage.goToEntity('link');
    linkComponentsPage = new LinkComponentsPage();
    await browser.wait(ec.visibilityOf(linkComponentsPage.title), 5000);
    expect(await linkComponentsPage.getTitle()).to.eq('cftcApp3App.link.home.title');
    await browser.wait(ec.or(ec.visibilityOf(linkComponentsPage.entities), ec.visibilityOf(linkComponentsPage.noResult)), 1000);
  });

  it('should load create Link page', async () => {
    await linkComponentsPage.clickOnCreateButton();
    linkUpdatePage = new LinkUpdatePage();
    expect(await linkUpdatePage.getPageTitle()).to.eq('cftcApp3App.link.home.createOrEditLabel');
    await linkUpdatePage.cancel();
  });

  it('should create and save Links', async () => {
    const nbButtonsBeforeCreate = await linkComponentsPage.countDeleteButtons();

    await linkComponentsPage.clickOnCreateButton();

    await promise.all([
      linkUpdatePage.theRoleSelectLastOption(),
      linkUpdatePage.setImageInput(absolutePath),
      linkUpdatePage.setTheLinkInput('theLink'),
      linkUpdatePage.titleFrSelectLastOption(),
      linkUpdatePage.titleEnSelectLastOption(),
      linkUpdatePage.descriptionFrSelectLastOption(),
      linkUpdatePage.descriptionEnSelectLastOption(),
      linkUpdatePage.subSelectLastOption(),
      linkUpdatePage.unSubSelectLastOption(),
    ]);

    expect(await linkUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
    expect(await linkUpdatePage.getTheLinkInput()).to.eq('theLink', 'Expected TheLink value to be equals to theLink');

    await linkUpdatePage.save();
    expect(await linkUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await linkComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Link', async () => {
    const nbButtonsBeforeDelete = await linkComponentsPage.countDeleteButtons();
    await linkComponentsPage.clickOnLastDeleteButton();

    linkDeleteDialog = new LinkDeleteDialog();
    expect(await linkDeleteDialog.getDialogTitle()).to.eq('cftcApp3App.link.delete.question');
    await linkDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(linkComponentsPage.title), 5000);

    expect(await linkComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
