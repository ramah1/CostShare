import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('UserCost e2e test', () => {

    let navBarPage: NavBarPage;
    let userCostDialogPage: UserCostDialogPage;
    let userCostComponentsPage: UserCostComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserCosts', () => {
        navBarPage.goToEntity('user-cost-cs');
        userCostComponentsPage = new UserCostComponentsPage();
        expect(userCostComponentsPage.getTitle()).toMatch(/costshareApp.userCost.home.title/);

    });

    it('should load create UserCost dialog', () => {
        userCostComponentsPage.clickOnCreateButton();
        userCostDialogPage = new UserCostDialogPage();
        expect(userCostDialogPage.getModalTitle()).toMatch(/costshareApp.userCost.home.createOrEditLabel/);
        userCostDialogPage.close();
    });

    it('should create and save UserCosts', () => {
        userCostComponentsPage.clickOnCreateButton();
        userCostDialogPage.setMultiplierInput('5');
        expect(userCostDialogPage.getMultiplierInput()).toMatch('5');
        userCostDialogPage.baseCostSelectLastOption();
        userCostDialogPage.userSelectLastOption();
        userCostDialogPage.save();
        expect(userCostDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserCostComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-cost-cs div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserCostDialogPage {
    modalTitle = element(by.css('h4#myUserCostLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    multiplierInput = element(by.css('input#field_multiplier'));
    baseCostSelect = element(by.css('select#field_baseCost'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setMultiplierInput = function (multiplier) {
        this.multiplierInput.sendKeys(multiplier);
    }

    getMultiplierInput = function () {
        return this.multiplierInput.getAttribute('value');
    }

    baseCostSelectLastOption = function () {
        this.baseCostSelect.all(by.tagName('option')).last().click();
    }

    baseCostSelectOption = function (option) {
        this.baseCostSelect.sendKeys(option);
    }

    getBaseCostSelect = function () {
        return this.baseCostSelect;
    }

    getBaseCostSelectedOption = function () {
        return this.baseCostSelect.element(by.css('option:checked')).getText();
    }

    userSelectLastOption = function () {
        this.userSelect.all(by.tagName('option')).last().click();
    }

    userSelectOption = function (option) {
        this.userSelect.sendKeys(option);
    }

    getUserSelect = function () {
        return this.userSelect;
    }

    getUserSelectedOption = function () {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
