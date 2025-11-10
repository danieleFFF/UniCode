import {CredentialsModel} from './credentials.model';

export interface RegisterPayload  extends CredentialsModel {
  username: string;
}
