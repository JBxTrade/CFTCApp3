import { ISub } from 'app/entities/sub/sub.model';
import { ILink } from 'app/entities/link/link.model';
import { ILinkData } from 'app/entities/link-data/link-data.model';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { TheRole } from 'app/entities/enumerations/the-role.model';

export interface IMain {
  id?: number;
  theRole?: TheRole;
  imageContentType?: string;
  image?: string;
  subLink?: ISub | null;
  link?: ILink | null;
  linkData?: ILinkData | null;
  titleFr?: II18n | null;
  titleEn?: II18n | null;
  descriptionFr?: II18n | null;
  descriptionEn?: II18n | null;
}

export class Main implements IMain {
  constructor(
    public id?: number,
    public theRole?: TheRole,
    public imageContentType?: string,
    public image?: string,
    public subLink?: ISub | null,
    public link?: ILink | null,
    public linkData?: ILinkData | null,
    public titleFr?: II18n | null,
    public titleEn?: II18n | null,
    public descriptionFr?: II18n | null,
    public descriptionEn?: II18n | null
  ) {}
}

export function getMainIdentifier(main: IMain): number | undefined {
  return main.id;
}
