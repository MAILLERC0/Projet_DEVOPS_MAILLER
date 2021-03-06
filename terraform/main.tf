terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 0.14.9"
}

data "template_file" "user_data" {
  template = file("add-ssh-web-app.yaml")
}
provider "aws" {
  profile                 = "default"
  region                  = "us-east-2"
  shared_credentials_file = "./credentials/credentials"
}

resource "aws_instance" "app_server" {
  ami                         = "ami-0d97ef13c06b05a19"
  instance_type               = "t2.micro"
  count                       = 1
  key_name                    = "deployer_MAILLERC_dev"
  associate_public_ip_address = "true"
  vpc_security_group_ids      = [aws_security_group.Resource_MAILLERC.id,aws_security_group.Resource_MAILLERC_2.id]
  user_data                   = data.template_file.user_data.rendered

  tags = {
    Name   = "${var.instance_name}-${count.index}"
    Groups = "app"
    Owner  = "MAILLER Corentin"
  }
}

resource "aws_key_pair" "deployer" {
  key_name   = "deployer_MAILLERC_dev"
  public_key = file("./ssh/id_rsa.pub")
}

resource "aws_security_group" "Resource_MAILLERC" {
  name        = "Resource_MAILLERC"
  ingress {
    from_port        = 22
    to_port          = 22
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
  tags = {
    Name   = var.instance_name
    Groups = "app"
    Owner  = "MAILLER Corentin"
  }
}

  resource "aws_security_group" "Resource_MAILLERC_2" {
  name        = "Resource_MAILLERC_2"

  ingress {
    from_port        = 8080
    to_port          = 8080
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name   = var.instance_name
    Groups = "app"
    Owner  = "MAILLER Corentin"
  }
}
