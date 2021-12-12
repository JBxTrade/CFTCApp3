import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { ISub } from 'app/entities/sub/sub.model';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { TheRole } from 'app/entities/enumerations/the-role.model';

export interface ILink {
  id?: number;
  theRole?: TheRole;
  imageContentType?: string;
  image?: string;
  theLink?: string;
  titleFr?: II18n | null;
  titleEn?: II18n | null;
  descriptionFr?: II18n | null;
  descriptionEn?: II18n | null;
  sub?: ISub | null;
  unSub?: IUnSub | null;
}

export class Link implements ILink {
  constructor(
    public id?: number,
    public theRole?: TheRole,
    public imageContentType?: string,
    public image?: string,
    public theLink?: string,
    public titleFr?: II18n | null,
    public titleEn?: II18n | null,
    public descriptionFr?: II18n | null,
    public descriptionEn?: II18n | null,
    public sub?: ISub | null,
    public unSub?: IUnSub | null
  ) {}
}

export function getLinkIdentifier(link: ILink): number | undefined {
  return link.id;
}
